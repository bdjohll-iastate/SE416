interface Notification {
    public void send(String message);
}

class EmailNotification implements Notification{
    public void send(String message){/* ... */}
}

class SMSNotification implements Notification{
    public void send(String message){/* ... */}
}

class PushNotification implements Notification{
    public void send(String message){/* ... */}
}

enum NotificationChannelType{EMAIL,SMS,PUSH} 

class NotificationFactory{
    public static Notification createNotification(NotificationChannelType channelType){
        switch(channelType){
            case EMAIL:
                return new EmailNotification();
            case SMS:
                return new SMSNotification();
            case PUSH:
                return new PushNotification();
            default:
                return null;
        }
    }
}

public class FactoryPattern{
    public static void main(String[] args){
        Notification notif = NotificationFactory.createNotification(NotificationChannelType.EMAIL);
        notif.send("Hello!");
    }
}