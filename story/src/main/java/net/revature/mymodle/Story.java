package net.revature.mymodle;


import java.util.Objects;

public class Story {
	
	
	private String authorname;
	private String title;
	private String completiondata ;
	private String genre;
	private String lengthOfstory;
	private String onesentence;
	private String description;
	private String status;
	private int id;
	
	public Story() {
		authorname="";
		title="";
		completiondata=("mm,dd,yyyy");
	    genre="";
		lengthOfstory="please type short or long";
	    onesentence="";
	    description="";
	    setStatus("Pending");
	    id=0;
	    
	}

	public String getAuthorname() {
		return authorname;
	}

	public void setAuthorname(String authorname) {
		this.authorname = authorname;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCompletiondata() {
		return completiondata;
	}

	public void setCompletiondata(String completiondata) {
		this.completiondata = completiondata;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getLengthOfstory() {
		return lengthOfstory;
	}

	public void setLengthOfstory(String lengthOfstory) {
		this.lengthOfstory = lengthOfstory;
	}

	public String getOnesentence() {
		return onesentence;
	}

	public void setOnesentence(String onesentence) {
		this.onesentence = onesentence;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Story [authorname=" + authorname + ", title=" + title + ", completiondata=" + completiondata
				+ ", genre=" + genre + ", lengthOfstory=" + lengthOfstory + ", onesentence=" + onesentence
				+ ", description=" + description + ", status=" + status + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(authorname, completiondata, description, genre, id, lengthOfstory, onesentence, status,
				title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Story other = (Story) obj;
		return Objects.equals(authorname, other.authorname) && Objects.equals(completiondata, other.completiondata)
				&& Objects.equals(description, other.description) && Objects.equals(genre, other.genre)
				&& id == other.id && Objects.equals(lengthOfstory, other.lengthOfstory)
				&& Objects.equals(onesentence, other.onesentence) && Objects.equals(status, other.status)
				&& Objects.equals(title, other.title);
	}
	
	
			            }          
			           	       
		
				       
		
			


