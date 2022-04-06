package net.revature.mymodle;

	import java.util.ArrayList;
	import java.util.List;
import java.util.Objects;
	public class Users{
		private int id;
		private String username;
		private String password;
		private String firstName;
		private String lastName;

		private List<Story> Storys;
		public Users() {
			id=0;
			username="";
			password="";
			firstName="";
			lastName="";
			Storys = new ArrayList<Story>();
		
			}

			public int getId() {
				return id;
			}

			public void setId(int id) {
				this.id = id;
			}

			public String getUsername() {
				return username;
			}

			public void setUsername(String username) {
				this.username = username;
			}

			public String getPassword() {
				return password;
			}

			public void setPassword(String password) {
				this.password = password;
			}

			public String getFirstName() {
				return firstName;
			}

			public void setFirstName(String firstName) {
				this.firstName = firstName;
			}

			public String getLastName() {
				return lastName;
			}

			public void setLastName(String lastName) {
				this.lastName = lastName;
			}
			public List<Story>getStorys() {
				return Storys;
			}

			public void setStorys(List<Story> Storys) {
				this.Storys = Storys;
			}
				@Override
			public String toString() {
				return "Users [id=" + id + ", username=" + username + ", password=" + password + ", firstName="
						+ firstName + ", lastName=" + lastName + ", Storys=" + Storys + "]";
			}

				@Override
				public int hashCode() {
					return Objects.hash(Storys, firstName, id, lastName, password, username);
				}

				@Override
				public boolean equals(Object obj) {
					if (this == obj)
						return true;
					if (obj == null)
						return false;
					if (getClass() != obj.getClass())
						return false;
					Users other = (Users) obj;
					return Objects.equals(Storys, other.Storys) && Objects.equals(firstName, other.firstName)
							&& id == other.id && Objects.equals(lastName, other.lastName)
							&& Objects.equals(password, other.password) && Objects.equals(username, other.username);
				
				}

				public String getStatus(){
					return getStatus();
				
				}

				public String getDescription() {
					// TODO Auto-generated method stub
					return getDescription();
				}

				public String getAuthorame() {
					// TODO Auto-generated method stub
					return null;
				}

				public String getTitle() {
					// TODO Auto-generated method stub
					return null;
				}

				public String getCompletiondata() {
					// TODO Auto-generated method stub
					return null;
				}

				public String getGenre() {
					// TODO Auto-generated method stub
					return null;
				}

				public String getLengthOfstory() {
					// TODO Auto-generated method stub
					return null;
				}

				public String getOnesentence() {
					// TODO Auto-generated method stub
					return null;
				}
				
			
}