package com.luretechnologies.tms.ui.view.admin.user;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.annotation.PrototypeScope;

import com.vaadin.data.provider.AbstractBackEndDataProvider;
import com.vaadin.data.provider.Query;
import com.vaadin.spring.annotation.SpringComponent;
import com.luretechnologies.tms.backend.data.entity.User;
import com.luretechnologies.tms.backend.service.MockUserService;

@SpringComponent
@PrototypeScope
public class UserAdminDataProvider extends AbstractBackEndDataProvider<User, Object> {

	private static final long serialVersionUID = -3459665913532137667L;
	private  MockUserService mockUserService;

	@Autowired
	public UserAdminDataProvider(MockUserService userService) {
		this.mockUserService = userService;
	}
	
	  @Override
	  public Object getId(User item) {
	    // TODO Auto-generated method stub
	    return item.getId();
	  }
	@Override
	public boolean isInMemory() {
		// TODO Auto-generated method stub
		return true;
	}
   
	@Override
	public  Stream<User> fetch(Query<User,Object> query) {
		return getUsers(query).stream();
	}

	@Override
	protected Stream<User> fetchFromBackEnd(Query<User, Object> query) {
		return getUsers(query).stream();
	}



	@Override
	protected int sizeInBackEnd(Query<User, Object> query) {
		getUsers(query).size();
		return 0;
	}
	
	private List<User> getUsers(Query<User, Object> query)
	{
		List<User> resultList =  mockUserService.getUsers();
		
		if(query.getFilter().isPresent())
			resultList = mockUserService.getUsers().stream().filter(u -> u.getEmail().contains(query.getFilter().get().toString()) ).collect(Collectors.toList());
		return resultList;
	}

}