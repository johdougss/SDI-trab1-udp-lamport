package sdi.entity;

/**
 *
 * @author Johnathan Douglas S. Santos
 */
public class Status {

    private boolean sucess;
    private String message;

    public Status(boolean sucess, String message) {
        this.sucess = sucess;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSucess() {
        return sucess;
    }

    public void setSucess(boolean sucess) {
        this.sucess = sucess;
    }

    @Override
    public String toString() {
        return "Status{" + "sucess=" + sucess + ", message=" + message + '}';
    }
}
