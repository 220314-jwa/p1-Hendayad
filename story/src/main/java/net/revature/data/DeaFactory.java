package net.revature.data;

public class DeaFactory {
	
	 private static StoryDao storyDAO = null;
	 private static UserDao userDAO = null;

	    // make our constructor private, so it can't be accessed publicly
	    private DeaFactory() {

	    }

	    public static  StoryDao getStoryDao() {
	        // make sure we're not recreating the dao if it already exists:
	        if (storyDAO == null) {
	            storyDAO = new StoryPostgress();
	        }
	        return storyDAO;
	    }
       public static UserDao getUserDAO() {
    	   if(userDAO==null)
    		   userDAO=new UserPostgress();
    		return userDAO;   
       }
      
       
      
	

		

}
