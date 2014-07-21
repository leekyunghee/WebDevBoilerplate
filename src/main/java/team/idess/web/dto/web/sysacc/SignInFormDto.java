package team.idess.web.dto.web.sysacc;

public class SignInFormDto {
	String userId;
	String password;
	String successSignIn;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSuccessSignIn() {
		return successSignIn;
	}

	public void setSuccessSignIn(String successSignIn) {
		this.successSignIn = successSignIn;
	}

	@Override
	public String toString() {
		return "SignInForm [userId=" + userId + ", password=" + password
				+ ", successSignIn=" + successSignIn + "]";
	}
}
