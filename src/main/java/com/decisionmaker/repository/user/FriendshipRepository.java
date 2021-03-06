package com.decisionmaker.repository.user;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decisionmaker.domain.user.Friendship;
import com.decisionmaker.domain.user.key.FriendshipPK;
import com.decisionmaker.exception.AlreadyFriendsException;
import com.decisionmaker.exception.EntityDoesNotExistException;
import com.decisionmaker.exception.IllegalFriendException;
import com.decisionmaker.factory.user.FriendshipFactory;
import com.decisionmaker.repository.AbstractDecisionMakerRepository;

@SuppressWarnings("unchecked")
@Repository
@Transactional
@Qualifier("friendshipRepository")
public class FriendshipRepository extends AbstractDecisionMakerRepository<Friendship, FriendshipPK>
		implements IFriendshipRepository {
	
	@Value("${friendship.delete.hql}")
	private String deleteHql;
	
	@Autowired
	private IFriendRequestRepository friendRequestRepository;

	@Override
	public List<Long> retrieveFriendIds(Long id) {
		return (List<Long>) sessionFactory.getCurrentSession().createCriteria(clazz)
				.add(Restrictions.eq("id.userId", id))
				.setProjection(Projections.property("id.friendId"))
				.addOrder(Order.asc("id.friendId"))
				.list();
	}
	
	@Override
	public Set<Friendship> discoverUserIdIsFriendOf(Long userId) {
		List<Friendship> friends = sessionFactory.getCurrentSession()
				.createCriteria(clazz)
				.add(Restrictions.eq("id.userId", userId))
				.list();
		return new HashSet<Friendship>(friends);
	}

	@Override
	public Set<Friendship> discoverFriendsOfUserId(Long userId) {
		List<Friendship> friends = sessionFactory.getCurrentSession()
				.createCriteria(clazz)
				.add(Restrictions.eq("id.friendId", userId))
				.list();
		return new HashSet<Friendship>(friends);
	}

	@Override
	public void addFriend(Long userId, Long possibleFriendId) throws AlreadyFriendsException, IllegalFriendException {
		if (userId == possibleFriendId) {
			throw new IllegalFriendException("The user and the possible friend id's are the same");
		}
		
		FriendshipPK id = FriendshipFactory.newPKInstance(userId, possibleFriendId);
		FriendshipPK reverseId = FriendshipFactory.newPKInstance(possibleFriendId, userId);
		
		if (doesEntityExistById(id) || doesEntityExistById(reverseId)) {
			throw new AlreadyFriendsException("The users: { " + id.toString() +" } are already friends");
		}
		
		Friendship friendship = FriendshipFactory.newInstance(id);
		saveEntity(friendship);
		
		Friendship reverseFriendship = FriendshipFactory.newInstance(reverseId);
		saveEntity(reverseFriendship);
		
		try{
			friendRequestRepository.deleteEntityById(id);
		} catch(EntityDoesNotExistException e) {
		}
	}

	@Override
	public void removeFriend(Long userId, Long friendId) throws EntityDoesNotExistException {
		FriendshipPK id = FriendshipFactory.newPKInstance(userId, friendId);
		FriendshipPK reverseId = FriendshipFactory.newPKInstance(friendId, userId);
		
		deleteEntityById(id);
		deleteEntityById(reverseId);
	}
	
	@Override
	protected void setClazz() {
		this.clazz = Friendship.class;
	}


}