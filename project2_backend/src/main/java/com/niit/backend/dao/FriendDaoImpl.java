package com.niit.backend.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.backend.model.Friend;
import com.niit.backend.model.User;



@Repository
public class FriendDaoImpl implements FriendDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	
	public List<User> getSuggestedUsers(User user) {
		
		Session session = sessionFactory.openSession();
		SQLQuery query = session.createSQLQuery("select *from userstable where username in (select username from userstable where username!=? minus (select from_id from friendtable where to_id=? and status!=?"
		                       +"union select to_id from friendtable where from_id=? and status!=?"
				               +"))");
		query.setString(0, user.getUsername());
		query.setString(1, user.getUsername());
		query.setString(3, user.getUsername());
		query.setString(2, "D");
		query.setString(4, "D");
		query.addEntity(User.class);
		List<User> users = query.list();
		session.close();
		return users;
	}
	
     public void friendRequest(String from,String to){
	    Session session = sessionFactory.openSession();
	    Friend friend = new Friend();
	    friend.setFrom(from);
	    friend.setTo(to);
	    friend.setStatus('P');
	    session.saveOrUpdate(friend);
	    session.flush();
	    session.close();
	}

	public List<Friend> pendingRequests(String toUsername) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Friend where to_id=? and status=?");
        query.setString(0, toUsername);
        query.setCharacter(1, 'P');
        List<Friend> friendRequests = query.list();
        session.close();
		return friendRequests;
	}

	public void updatePendingRequests(String from, String username, char status) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("update Friend set status=? where from_id = ? and to=? ");
		query.setCharacter(0, status);
		query.setString(1, from);
		query.setString(2, username);
		query.executeUpdate();
		session.flush();
		session.close();
	}
    
	public List<Friend> listOfFriends(String username){
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Friend where (from_id = ? or to_id=? ) and status='A'");
		query.setString(0, username);
		query.setString(1, username);
		List<Friend> friends = query.list();
		session.close();
		return friends;
	}
	
	public List<User> allUsers(){
		Session session = sessionFactory.openSession();;
		Query query = session.createQuery("from User");
		List<User> users = query.list();
		session.close();
		return users;
	}
}
