package team.idess.web.dto.web.sysacc;

public class LoginFormDto {
	String userId;
	String password;
	String successLogin;

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

	public String getSuccessLogin() {
		return successLogin;
	}

	public void setSuccessLogin(String successLogin) {
		this.successLogin = successLogin;
	}

	@Override
	public String toString() {
		return "LoginFormDto [userId=" + userId + ", password=" + password
				+ ", successLogin=" + successLogin + "]";
	}

}
