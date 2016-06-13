package au.com.capitalradiology.model;

/**
 * Created by Mehul on 22-06-15.
 */
public class BinForContactEmail
{
    private String username;
    private String emailId;
    private boolean selected = false;

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
