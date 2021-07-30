package com.guojianyong.service.impl;


import com.guojianyong.dao.*;
import com.guojianyong.dao.impl.simpleMBatis.session.SqlSession;
import com.guojianyong.exception.DaoException;
import com.guojianyong.model.*;
import com.guojianyong.model.vo.MemberVO;
import com.guojianyong.service.ChatService;
import com.guojianyong.service.constants.ChatType;
import com.guojianyong.service.constants.MessageType;
import com.guojianyong.service.constants.ServiceMessage;
import com.guojianyong.service.constants.Status;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import static com.guojianyong.utils.StringUtils.toLegalText;
import static com.guojianyong.utils.UUIDUtils.getUUID;


public class ChatServiceImpl implements ChatService {

    private final UserDao userDao = SqlSession.getMapper(UserDao.class);
    private final ChatDao chatDao = SqlSession.getMapper(ChatDao.class);
    private final MemberDao memberDao = SqlSession.getMapper(MemberDao.class);
    private final MessageDao messageDao = SqlSession.getMapper(MessageDao.class);
    private final FriendDao friendDao = (SqlSession.getMapper(FriendDao.class));

    /**
     * 创建一个聊天,如果是群聊，必须指定外部唯一标识（群号），如果是私聊则自动使用uuid作为唯一标识
     * @param chat
     * @param isGroupChat
     * @return
     */
    @Override
    public ServiceResult createChat(Chat chat, boolean isGroupChat) {
        //检查参数
        if (chat == null) {
            return new ServiceResult(Status.ERROR, ServiceMessage.PARAMETER_NOT_ENOUGHT.message, chat);
        }
        //阻止插入id，数据库自动增长
        chat.setId(null);
        //检查创建用户是否存在
        if (userDao.getUserById(chat.getOwnerId()) == null) {
            return new ServiceResult(Status.ERROR, ServiceMessage.ACCOUNT_NOT_FOUND.message, chat);
        }
        //判断聊天类型
        if (isGroupChat) {
            //检查群号是否合法
            if (!isValidChatNumber(chat.getNumber())) {
                return new ServiceResult(Status.ERROR, ServiceMessage.CHAT_NUMBER_INVALID.message, chat);
            }
            //群名
            chat.setName(toLegalText(chat.getName()));
            //检查群号是否已存在
            if (chatDao.getByChatNumber(chat.getNumber()) != null) {
                return new ServiceResult(Status.ERROR, ServiceMessage.CHAT_NUMBER_ALREADT_EXIST.message, chat);
            }
            //设置聊天类型为群聊
            chat.setType(ChatType.GROUP.toString());
        } else {
            //使用uuid作为外部标识
            if (chat.getNumber() == null) {
                chat.setNumber(getUUID());
            }
        }
        try {
            //将该聊天插入数据库
            if (chatDao.insert(chat) != 1) {
                return new ServiceResult(Status.ERROR, ServiceMessage.CREATE_CHAT_FAILED.message, chat);
            }
        } catch (DaoException e) {
            e.printStackTrace();
            return new ServiceResult(Status.ERROR, ServiceMessage.DATABASE_ERROR.message, chat);
        }
        //插入成功将其从数据库查出，以便返回id
        chat = chatDao.getByChatNumber(chat.getNumber());
        return new ServiceResult(Status.SUCCESS, ServiceMessage.CREATE_CHAT_SUCCESS.message, chat);
    }

    /**
     * 给已是好友关系创建一个聊天关系，并把两者加入到此聊天，并更新好友关系上的聊天关系id
     * @param friend
     * @return
     */
    @Override
    public ServiceResult createFriendChat(Friend friend) {
        if (friend == null) {
            return new ServiceResult(Status.ERROR, ServiceMessage.PARAMETER_NOT_ENOUGHT.message, null);
        }
        //不与自己建立聊天
        if (friend.getFriendId().equals(friend.getUserId())) {
            return new ServiceResult(Status.ERROR, ServiceMessage.CANNOT_ADD_YOURSELF.message, null);
        }
        Chat chat = new Chat();
        //这里查到双向添加好友说明对方先加我为好友，聊天所有者为对方
        chat.setOwnerId(friend.getFriendId());
        chat = (Chat) createChat(chat, false).getData();
        //创建两个成员，将双方加入聊天中
        Member member1 = new Member();
        member1.setUserId(friend.getUserId());
        member1.setChatId(chat.getId());
        Member member2 = new Member();
        member2.setUserId(friend.getFriendId());
        member2.setChatId(chat.getId());
        joinChat(new Member[]{member1, member2});
        //添加成功后将聊天id更新到朋友信息中
        try {
            //更新好友信息
            friend = friendDao.getFriendByUIDAndFriendId(friend.getUserId(), friend.getFriendId());
            if (friend == null) {
                return new ServiceResult(Status.ERROR, ServiceMessage.NOT_FOUND.message, chat);
            }
            friend.setChatId(chat.getId());
            friendDao.update(friend);
            friend = friendDao.getFriendByUIDAndFriendId(friend.getFriendId(), friend.getUserId());
            if (friend == null) {
                return new ServiceResult(Status.ERROR, ServiceMessage.NOT_FOUND.message, chat);
            }
            friend.setChatId(chat.getId());
            friendDao.update(friend);


        } catch (DaoException e) {
            e.printStackTrace();
            return new ServiceResult(Status.ERROR, ServiceMessage.DATABASE_ERROR.message, chat);
        }
        return new ServiceResult(Status.SUCCESS, ServiceMessage.CREATE_CHAT_SUCCESS.message, chat);
    }

    /**
     * 通过群号将一个用户添加到群聊中
     * @param userId
     * @param number
     * @param apply
     * @return
     */
    @Override
    public ServiceResult joinChatByNumber(BigInteger userId, String number, String apply) {
        if (userId == null | number == null) {
            return new ServiceResult(Status.ERROR, ServiceMessage.PARAMETER_NOT_ENOUGHT.message, null);
        }
        //通过群号获取群id
        Chat chat = chatDao.getByChatNumber(number);
        if (chat == null) {
            return new ServiceResult(Status.ERROR, ServiceMessage.CHAT_NOT_FOUND.message, number);
        }
        //通过群id和用户id创建一个成员
        Member member = new Member();
        member.setChatId(chat.getId());
        member.setUserId(userId);
        member.setApply(apply);
        ServiceResult result = joinChat(new Member[]{member});
        if (Status.ERROR.equals(result.getStatus())) {
            return result;
        }
        return new ServiceResult(Status.SUCCESS, ServiceMessage.JOIN_GROUP_CHAT_SUCCESS.message, member);
    }

    /**
     * 查询一个聊天中所有成员的信息
     * @param chatId 聊天id
     * @return
     */
    @Override
    public ServiceResult listMember(BigInteger chatId) {
        if (chatId == null) {
            return new ServiceResult(Status.ERROR, ServiceMessage.PARAMETER_NOT_ENOUGHT.message, chatId);
        }
        List<MemberVO> memberVOList;
        try {
            //检查聊天是否存在
            if (chatDao.getChatById(chatId) == null) {
                return new ServiceResult(Status.ERROR, ServiceMessage.CHAT_NOT_FOUND.message, chatId);
            }
            memberVOList = new LinkedList<>();
            List<Member> memberList = memberDao.listMemberByChatId(chatId);
            //对每个成员将其用户信息查出来构建memberVO
            for (Member member : memberList) {
                User user = userDao.getUserById(member.getUserId());
                MemberVO memberVO = new MemberVO();
                memberVO.setName(user.getName());
                memberVO.setPhoto(user.getPhoto());
                memberVO.setSignature(user.getSignature());
                memberVO.setChatId(chatId);
                memberVO.setUserId(member.getUserId());
                memberVO.setBackground(member.getBackground());
                memberVO.setLevel(member.getLevel());
                memberVO.setId(member.getId());
                memberVOList.add(memberVO);
            }
        } catch (DaoException e) {
            e.printStackTrace();
            return new ServiceResult(Status.ERROR, ServiceMessage.DATABASE_ERROR.message, chatId);
        }
        return new ServiceResult(Status.SUCCESS, null, memberVOList);
    }


    /**防止对公共数据造成错误
     * 把用户添加到聊天中
     * @param members
     * @return
     */
    @Override
   synchronized public ServiceResult joinChat(Member[] members) {
        try {
            for (Member member : members) {
                //阻止插入id
                member.setId(null);
                Chat chat = chatDao.getChatById(member.getChatId());
                User user = userDao.getUserById(member.getUserId());
                //检查聊天是否存在
                if (chat == null) {
                    return new ServiceResult(Status.ERROR, ServiceMessage.CHAT_NOT_FOUND.message, members);
                }
                //检查用户是否存在
                if (user == null) {
                    return new ServiceResult(Status.ERROR, ServiceMessage.ACCOUNT_NOT_FOUND.message, members);
                }
                //检查该用户是否已在此聊天
                if (memberDao.getMemberByUIdAndChatId(member.getUserId(), member.getChatId()) != null) {
                    return new ServiceResult(Status.ERROR, ServiceMessage.MEMBER_ALREADY_EXIST.message, members);
                }
                //将群成员昵称设置为用户的昵称
                if (member.getGroupAlias() == null) {
                    member.setGroupAlias(user.getName());
                }
                //将该成员插入到此聊天
                if (memberDao.insert(member) != 1) {
                    return new ServiceResult(Status.ERROR, ServiceMessage.JOIN_CHAT_FAILED.message, members);
                }
                //将该聊天的成员加一
                chat.setMember(chat.getMember() + 1);
                chatDao.update(chat);
            }
        } catch (DaoException e) {
            e.printStackTrace();
            return new ServiceResult(Status.ERROR, ServiceMessage.DATABASE_ERROR.message, members);
        }
        return new ServiceResult(Status.SUCCESS, ServiceMessage.JOIN_CHAT_SUCCESS.message, members);
    }

    /**
     * 退出群聊
     * @param member
     * @return
     */
    @Override
    synchronized public ServiceResult quitChat(Member member) {
        if (member == null) {
            return new ServiceResult(Status.ERROR, ServiceMessage.PARAMETER_NOT_ENOUGHT.message, member);
        }
        try {
            member = memberDao.getMemberByUIdAndChatId(member.getUserId(), member.getChatId());
            ServiceResult result;
            result = removeFromChat(member.getId());
            if (Status.ERROR.equals(result.getStatus())) {
                return result;
            }
        } catch (DaoException e) {
            e.printStackTrace();
            return new ServiceResult(Status.ERROR, ServiceMessage.DATABASE_ERROR.message, member);
        }
        return new ServiceResult(Status.SUCCESS, ServiceMessage.QUIT_CHAT_SUCCESS.message, member);
    }

    /**
     * 将一个成员从聊天中移除
     * @param memberId
     * @return
     */
    @Override
    public ServiceResult removeFromChat(BigInteger memberId) {
        if(memberId==null){
            return new ServiceResult(Status.ERROR, ServiceMessage.PARAMETER_NOT_ENOUGHT.message, null);
        }
        try {
            //检查成员是否存在
            if (memberDao.getMemberById(memberId) == null) {
                return new ServiceResult(Status.ERROR, ServiceMessage.MEMBER_NOT_FOUND.message, null);
            }
            Member member = memberDao.getMemberById(memberId);
            Chat chat = chatDao.getChatById(member.getChatId());
            //不可移除好友
            if(ChatType.FRIEND.toString().equalsIgnoreCase(chat.getType())){
                return new ServiceResult(Status.ERROR, ServiceMessage.CANNOT_REMOVE_FRIEND.message, memberId);
            }

            //将该成员从聊天中移除
            if (memberDao.delete(member) != 1) {
                return new ServiceResult(Status.ERROR, ServiceMessage.REMOVE_MEMBER_FAILED.message, memberId);
            }
            //将该聊天的成员减一
            if (chat == null) {
                return new ServiceResult(Status.ERROR, ServiceMessage.CHAT_NOT_FOUND.message, memberId);
            }
            chat.setMember(chat.getMember() - 1);
            chatDao.update(chat);
        } catch (DaoException e) {
            e.printStackTrace();
            return new ServiceResult(Status.ERROR, ServiceMessage.DATABASE_ERROR.message, memberId);
        }
        return new ServiceResult(Status.SUCCESS, ServiceMessage.REMOVE_MEMBER_SUCCESS.message, memberId);
    }


    /**
     * 返回一个用户的所有聊天
     * @param user
     * @return
     */
    @Override
    public ServiceResult listChat(User user) {
        if (user == null) {
            return new ServiceResult(Status.ERROR, ServiceMessage.PARAMETER_NOT_ENOUGHT.message, user);
        }
        //检查用户是否存在
        if (userDao.getUserById(user.getId()) == null) {
            return new ServiceResult(Status.ERROR, ServiceMessage.ACCOUNT_NOT_FOUND.message, user);
        }
        List<Chat> chatVOList = new LinkedList<>();
        try {
            List<Chat> chatList = chatDao.listByUserId(user.getId());
            if (chatList == null || chatList.size() == 0) {
                return new ServiceResult(Status.SUCCESS, ServiceMessage.NO_CHAT_NOW.message, chatList);
            }
            for (Chat chat : chatList) {
                //在私聊中将聊天头像设置为对方头像
                if (ChatType.FRIEND.toString().equalsIgnoreCase(chat.getType())) {
                    chat = chatDao.toFriendChat(chat.getId(), user.getId());
                    if (chat == null) {
                        return new ServiceResult(Status.ERROR, ServiceMessage.DATABASE_ERROR.message, chatList);
                    }
                }
                chatVOList.add(chat);
            }
        } catch (DaoException e) {
            e.printStackTrace();
            return new ServiceResult(Status.ERROR, ServiceMessage.DATABASE_ERROR.message, user);
        }
        return new ServiceResult(Status.SUCCESS, null, chatVOList);
    }

    /**
     * 通过用户id和群号获取一个聊天
     * @param number
     * @param userId
     * @return
     */
    @Override
    public ServiceResult getChat(String number, BigInteger userId) {
        if (number == null || userId == null) {
            return new ServiceResult(Status.ERROR, ServiceMessage.PARAMETER_NOT_ENOUGHT.message, number);
        }
        Chat chat;
        try {
            chat = chatDao.getByChatNumber(number);
            //检查聊天是否存在
            if (chat == null) {
                return new ServiceResult(Status.ERROR, ServiceMessage.NOT_FOUND.message, chat);
            }
            //检查用户是否存在
            User user = userDao.getUserById(userId);
            if (user == null) {
                return new ServiceResult(Status.ERROR, ServiceMessage.ACCOUNT_NOT_FOUND.message, chat);
            }
            //检查是否是私聊
            if (ChatType.FRIEND.toString().equalsIgnoreCase(chat.getType())) {
                chat = chatDao.toFriendChat(chat.getId(), user.getId());
                if (chat == null) {
                    return new ServiceResult(Status.ERROR, ServiceMessage.DATABASE_ERROR.message, number);
                }
            }
        } catch (DaoException e) {
            e.printStackTrace();
            return new ServiceResult(Status.ERROR, ServiceMessage.DATABASE_ERROR.message, number);
        }
        return new ServiceResult(Status.SUCCESS, null, chat);
    }


    /**
     * 删除一个聊天
     * @param chat
     */
    @Override
    public void removeChat(Chat chat) {
        try {
            chatDao.delete(chat);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过聊天编号查询聊天
     * @param number
     */
    @Override
    public void getChatByNumber(Object number) {
        try {
            chatDao.getByChatNumber(number);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取打招呼消息
     * @param member
     * @return
     */
    @Override
    public Message getHelloMessage(Member member) {
        //生成打招呼消息
        Message message = new Message();
        message.setChatId(member.getChatId());
        message.setSenderId(member.getUserId());
        message.setTime(new Timestamp(System.currentTimeMillis()));
        message.setType(MessageType.USER.toString());
        message.setContent(member.getApply());
        return message;
    }

    /**
     * 用来检查一个操作移除群成员的用户是否是群主
     * @param memberId
     * @param userId
     * @return
     */
    @Override
    public boolean isOwner(BigInteger memberId, BigInteger userId) {
    if (memberId == null || userId == null) {
            return false;
        }
        Member member = memberDao.getMemberById(memberId);
        Chat chat = chatDao.getChatById(member.getChatId());
        if (userId.equals(chat.getOwnerId())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 群号的正则验证
     * @param chatNumber
     * @return
     */
    private boolean isValidChatNumber(String chatNumber) {
        if (chatNumber == null || chatNumber.trim().isEmpty()) {
            return false;
        }
        String regex = "[0-9]{6,20}$";
        return chatNumber.matches(regex);
    }


}
