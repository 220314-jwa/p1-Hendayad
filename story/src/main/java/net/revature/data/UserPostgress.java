package net.revature.data;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;



import net.revature.mymodle.Users;
import net.revature.utils.ConnectionFactory;

public class UserPostgress implements UserDao {

private static ConnectionFactory connFactory = ConnectionFactory.getConnectionFactory();
	
	@Override
	public int create(Users newObj) {
		Connection conn = connFactory.getConnection();
		try {
			String sql = "insert into users (id, username, passwd,lastname)"
					+ " values (default,?,?,?);";
			PreparedStatement pStmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			pStmt.setString(1, newObj.getFirstName() + " " + newObj.getLastName());
			pStmt.setString(2, newObj.getUsername());
			pStmt.setString(3, newObj.getPassword());
			pStmt.setInt(4, 1);
			
			conn.setAutoCommit(false); // for ACID (transaction management)
			pStmt.executeUpdate();
			ResultSet resultSet = pStmt.getGeneratedKeys();
			
			if (resultSet.next()) {
				newObj.setId(resultSet.getInt(1));
				conn.commit();
			} else {
				conn.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return newObj.getId();
	}

	@Override
	public Users getById(int id) {
		Users user = null;
		try (Connection conn = connFactory.getConnection()) {
			String sql = "select * from person join pet_owner on person.id=pet_owner.owner_id"
					+ " where person.id = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, id);
			
			ResultSet resultSet = pStmt.executeQuery();
			if (resultSet.next()) {
				user = new Users();
				user.setId(id);
				String fullName = resultSet.getString("full_name");
				user.setFirstName(fullName.substring(0, fullName.indexOf(' ')));
				user.setLastName(fullName.substring(fullName.indexOf(' ') + 1));
				user.setUsername(resultSet.getString("username"));
				user.setPassword(resultSet.getString("passwd"));
				
				StoryDao storyDao = DeaFactory.getStoryDao();
				user.setStorys(storyDao.getByOwner(user));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public List<Users> getAll() {
		List<Users> users = new LinkedList<>();
		try (Connection conn = connFactory.getConnection()) {
			// left join because we want ALL the people even if they don't have any pets.
			// a full join would be fine too since everything in the pet_owner table
			// will have a user associated with it, but a left join makes more sense logically
			String sql = "select * from person left join pet_owner on person.id=pet_owner.owner_id";
			Statement stmt = conn.createStatement();
			
			ResultSet resultSet = stmt.executeQuery(sql);
			while (resultSet.next()) {
				Users user = new Users();
				user.setId(resultSet.getInt("id"));
				String fullName = resultSet.getString("full_name");
				user.setFirstName(fullName.substring(0, fullName.indexOf(' ')));
				user.setLastName(fullName.substring(fullName.indexOf(' ') + 1));
				user.setUsername(resultSet.getString("username"));
				user.setPassword(resultSet.getString("passwd"));
				
				StoryDao storyDao = DeaFactory.getStoryDao();
				user.setStorys(storyDao.getByOwner(user));
				
				users.add(user);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	@Override
	public void update(Users updatedObj) {
		Connection conn = connFactory.getConnection();
		try {
			String sql = "update person set full_name=?, username=?, passwd=?, role_id=? "
					+ "where id=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, updatedObj.getFirstName() + " " + updatedObj.getLastName());
			pStmt.setString(2, updatedObj.getUsername());
			pStmt.setString(3, updatedObj.getPassword());
			pStmt.setInt(4, 1);
			pStmt.setInt(5, updatedObj.getId());
			
			conn.setAutoCommit(false); // for ACID (transaction management)
			int rowsUpdated = pStmt.executeUpdate();
			
			if (rowsUpdated<=1) {
				conn.commit();
			} else {
				conn.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void delete(Users objToDelete) {
		Connection conn = connFactory.getConnection();
		try {
			String sql = "delete from users where id=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, objToDelete.getId());
			
			conn.setAutoCommit(false); // for ACID (transaction management)
			int rowsUpdated = pStmt.executeUpdate();
			
			if (rowsUpdated<=1) {
				conn.commit();
			} else {
				conn.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Users getByUsername(String username) {
		Users user = null;
		try (Connection conn = connFactory.getConnection()) {
			String sql = "select * from users join storys on users.id=story_owner.owner_id"
					+ " where story.username = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, username);
			
			ResultSet resultSet = pStmt.executeQuery();
			if (resultSet.next()) {
				user = new Users();
				user.setId(resultSet.getInt("id"));
				String fullName = resultSet.getString("full_name");
				user.setFirstName(fullName.substring(0, fullName.indexOf(' ')));
				user.setLastName(fullName.substring(fullName.indexOf(' ') + 1));
				user.setUsername(username);
				user.setPassword(resultSet.getString("passwd"));
				
				StoryDao storyDao = DeaFactory.getStoryDao();
				user.setStorys(storyDao.getByOwner(user));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;

	}

	

}