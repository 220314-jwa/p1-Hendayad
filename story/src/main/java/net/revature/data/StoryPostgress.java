package net.revature.data;
import net.revature.mymodle.Users;
import net.revature.utils.ConnectionFactory;
import net.revature.mymodle.Story;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class StoryPostgress implements StoryDao{
private static ConnectionFactory connFactory = ConnectionFactory.getConnectionFactory();
 
	@Override
    // this method needs to insert the object into the database:
    // so, we need to connect to the database:
    public int create(Story newObj) {
		Connection connection = connFactory.getConnection();
		
        // this stores our sql command, that we would normally to DBeaver/command line
        //                             0   1     2        3            4    5    6       7             8            9         10         
        String sql = "insert into story (id, authorname, title, completiondata, genre, lengthofstory,onesentence,description,status,status_id)" +
                "values (default,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            // create a prepared statement, we pass in the sql command
            // also the flag "RETURN_GENERATED_KEYS" so we can get that id that is generated
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            // set the fields:
            preparedStatement.setString(1, newObj.getAuthorname());
            preparedStatement.setString(2, newObj.getTitle());
            preparedStatement.setString(3, newObj.getCompletiondata());
            preparedStatement.setString(4, newObj.getGenre());
            preparedStatement.setString(5, newObj.getLengthOfstory());
            preparedStatement.setString(6, newObj.getOnesentence());
            preparedStatement.setString(7, newObj.getDescription());
            
            // shortcut for now, but we need the corresponding id for the status
            int status_id;
            if (newObj.getStatus().equals("Pending")) {
                status_id = 1;
            }
            else {
                status_id = 2;
            }
            preparedStatement.setInt(8, status_id);
            
            connection.setAutoCommit(false); // for tx management (ACID)
            // execute this command, return number of rows affected:
            int count = preparedStatement.executeUpdate();
            // lets us return the id that is auto-generated
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            // if we affected one or more rows:
            if (count > 0) {
                System.out.println("story added!");
                // return the generated id:
                // before we call resultSet.next(), it's basically pointing to nothing useful
                // but moving that pointer allows us to get the information that we want
                resultSet.next();
                int id = resultSet.getInt(1);
                newObj.setId(id);
                connection.commit(); // commit the changes to the DB
            }
            // if 0 rows are affected, something went wrong:
            else {
                System.out.println("Something went wrong when trying to sybmit story!");
                connection.rollback(); // rollback the changes
            }
        } catch (SQLException e){
            // print out what went wrong:
            e.printStackTrace();
            try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
        } finally {
        	try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
        
        return newObj.getId();
    }

    @Override
    // take in an id, return the corresponding pet
    public Story getById(int id) {
        // initialize our pet object to be null:
        Story story = null;
        // placeholder for our final sql string
        // ? placeholder for our id:
        // * means all fields
        // but we specify an id so we only get one single pet:
        String sql = "SELECT * FROM story WHERE id = ?";

        try (Connection connection = connFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            // if result set doesn't point to a next value, that means something went wrong
            if(resultSet.next()) {
                story = parseResultSet(resultSet);
                // now, we've created a pet Java object based on the info from our table
            } else {
                System.out.println("Something went wrong when querying the story!");
                // return null in this case
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return story;
    }

	private Story parseResultSet(ResultSet resultSet)throws SQLException {
		    Story story=new Story();
		    story.setId(resultSet.getInt(1));
	        story.setAuthorname(resultSet.getString(2));
	        story.setTitle(resultSet.getString(3));
	        story.setCompletiondata(resultSet.getString(4));
	        story.setGenre(resultSet.getString(5));
	        story.setLengthOfstory(resultSet.getString(6));
	        story.setOnesentence(resultSet.getString(7));
	        story.setDescription(resultSet.getString(8));
	        int status_id = resultSet.getInt(9);
            String status=(status_id==1)?"Pending" : "Approved";
	        story.setStatus(status);
	        return story;
	}


	@Override
	public List<Story> getAll() {
		List<Story>storys=new ArrayList<Story>();
		String sql="SELECT*FROM story";
		  try (Connection connection = connFactory.getConnection()){
	            PreparedStatement preparedStatement = connection.prepareStatement(sql);
	            // get the result from our query:
	            ResultSet resultSet = preparedStatement.executeQuery();
	            // because the resultSet has multiple pets in it, we don't just want an if-statement. We want a loop:
	            while(resultSet.next()) {
	                Story story = parseResultSet(resultSet);
	                storys.add(story);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return storys;

		
	}

	@Override
	public void update(Story updatedObj) {
		Connection connection = connFactory.getConnection();
    	// we create the template for the SQL string:
    	String sql = "update pet set name = ?, species = ?, description = ?, age = ?, status_id = ? where id = ?;";
    	try {
        	PreparedStatement preparedStatement = connection.prepareStatement(sql);
        	// fill in the template:
        	preparedStatement.setString(1,updatedObj.getAuthorname());
        	preparedStatement.setString(2,updatedObj.getTitle());
        	preparedStatement.setString(3, updatedObj.getCompletiondata());
        	preparedStatement.setString(4,  updatedObj.getGenre());
        	preparedStatement.setString(5, updatedObj.getLengthOfstory());
        	preparedStatement.setString(6, updatedObj.getOnesentence());
        	preparedStatement.setString(7, updatedObj.getDescription());
        
        	// TODO: Check the status database for the real value:
        	int status_id = (updatedObj.getStatus().equals("Pending")) ? 1 : 2;
        	preparedStatement.setInt(8, status_id);
        	preparedStatement.setInt(9, updatedObj.getId());
        	
        	connection.setAutoCommit(false);
        	// return a count of how many records were updated
        	int count = preparedStatement.executeUpdate();
        	if(count != 1) {
        		System.out.println("Oops! Something went wrong with the update!");
        		connection.rollback();
        	} else connection.commit();
        	
    		
    	} catch(SQLException e) {
    		e.printStackTrace();
    		try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
    	} finally {
    		try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}

		
	}

	@Override
	public void delete(Story objToDelete) {
Connection connection = connFactory.getConnection();
    	
    	String sql = "delete from Story where id = ?;";
    	try {
    		PreparedStatement preparedStatement = connection.prepareStatement(sql);
    		preparedStatement.setInt(1, objToDelete.getId());
    		
    		connection.setAutoCommit(false);
    		int count = preparedStatement.executeUpdate();
    		if (count != 1) {
    			System.out.println("Something went wrong with the deletion!");
    			connection.rollback();
    		} else connection.commit();
    	} catch (SQLException e) {
    		e.printStackTrace();
    		try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
    	} finally {
    		try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
		
	}

	
	

	@Override
	public List<Story> getByOwner(Users owner) {
		List<Story> storys = new LinkedList<>();
    	try (Connection conn = connFactory.getConnection()) {
    		String sql = "select * from story join pet_owner on pet.id=pet_owner.pet_id"
    				+ " where story_owner.owner_id=?";
    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		pStmt.setInt(1, owner.getId());
    		
    		ResultSet resultSet = pStmt.executeQuery();
    		while (resultSet.next()) {
    			Story story = parseResultSet(resultSet);
    			storys.add(story);
    		}
    	} 
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
	
        return storys;
    }

	

	@Override
	public List<Story> getByStatus(String status) {
		List<Story> storys = new LinkedList<>();
    	try (Connection conn = connFactory.getConnection()) {
    		String sql = "select * from pet where status_id=?";
    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		// may need modified later if new statuses are added
    		int statusId = (status.equals("Pending")?1:2);
    		pStmt.setInt(1, statusId);
    		
    		ResultSet resultSet = pStmt.executeQuery();
    		while (resultSet.next()) {
    			Story story = parseResultSet(resultSet);
    			storys.add(story);
    		}
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    	
        return storys;

		
	}

	


}
