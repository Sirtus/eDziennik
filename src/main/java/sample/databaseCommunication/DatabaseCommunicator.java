package sample.databaseCommunication;


import javax.persistence.EntityManager;


public class DatabaseCommunicator {
    private int userId;
    private final EntityManager session;
    private Login login;

    public DatabaseCommunicator(EntityManager session){
        this.session = session;
    }

    public int signIn(String login, String password, Login type){
        userId = Login.login(session, login, password, type);
        if(userId != -1) this.login = type;
        System.out.println(userId);
        return userId;
    }

    public Login getUserType(){
        return login;
    }

}
