package etf.project.model.container;

import java.io.Serializable;

/**
 * @author NemanjaDavidovic
 * @since 27.01.2023.
 * 
 * <p> It serves to transfer password to the server - user database (InMemoryDataBase). </p>
 *
 */

public class PasswordContainer implements Serializable {

private static final long serialVersionUID = 1L;
	
	private String oldPassword, newPassword;
	
	public PasswordContainer() {
		super();
	}

	public PasswordContainer(String oldPassword, String newPassword) {
		super();
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@Override
	public String toString() {
		return "PasswordContainer [oldPassword=" + oldPassword + ", newPassword=" + newPassword + "]";
	}
	
	
}
