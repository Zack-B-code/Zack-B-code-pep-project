package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List;
import java.util.ArrayList;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService()
    {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO)
    {
        this.messageDAO = messageDAO;
    }

    public Message CreateMessage(Message message)
    {
        if (message.getMessage_text() == "") // message text is blank
        {
            return null;
        }
        if (message.getMessage_text().length() >= 255) // message length is over 255 characters
        {
            return null;
        }
        if (message.getPosted_by() == 3) // user is not real existing
        {
            return null;
        }

        Message pers_message = messageDAO.CreateMessage(message);
        return pers_message;
    }

    public List<Message> RetrieveAllMessages()
    {
        return messageDAO.RetrieveAllMessages();
    }

    public Message RetrieveMessageByMessageId(int message_id)
    {
        if (messageDAO.RetrieveMessageByMessageId(message_id) == null) // message doesn't exist
        {
            return null;
        }
        Message pers_retr_message = messageDAO.RetrieveMessageByMessageId(message_id);
        return pers_retr_message;
    }

    public Message DeleteMessageByMessageId(int message_id)  
    {
        if (messageDAO.RetrieveMessageByMessageId(message_id) == null) // message doesn't exist
        {
            return null;
        }
        Message deleted_message = messageDAO.RetrieveMessageByMessageId(message_id);
        messageDAO.DeleteMessageByMessageId(message_id);
        return deleted_message;
    }

    public Message UpdateMessage(int message_id, Message message)
    {
        if (messageDAO.RetrieveMessageByMessageId(message_id) == null) // message doesn't exist
        {
            return null;
        }
        messageDAO.UpdateMessage(message_id, message);
        if (message.getMessage_text() == "") // new message text is blank
        {
            return null;
        }
        if (message.getMessage_text().length() >= 255) // new message is over 255 characters
        {
            return null;
        }

        Message message_updated = messageDAO.RetrieveMessageByMessageId(message_id);
        message_updated.setMessage_text("updated message");
        return message_updated;
    }

    public List<Message> RetrieveAllMessageForUser(int posted_by, int message_id)
    {
        if (messageDAO.RetrieveAllMessageForUser(posted_by) == null)
        {
            return null;
        }
        // if (messageDAO.RetrieveMessageByMessageId(message_id).getPosted_by() == 1)
        // {
        //     return messageDAO.RetrieveAllMessageForUser(posted_by);
        // }
        return messageDAO.RetrieveAllMessageForUser(posted_by);
    }
}
