package fys.fyspersistance.messages;

import fys.fysmodel.Message;
import fys.fysmodel.User;
import fys.fyspersistance.Repository;

public interface MessagesRepository extends Repository<Integer, Message> {
    Iterable<Message> getUserMessages(User user);
}
