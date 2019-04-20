package mx.ipn.escom.util;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

import mx.ipn.escom.chatsockets.entity.User;

public class UsersModel extends AbstractListModel {
	private ArrayList<User> usersList = new ArrayList<User>();
	@Override
    public int getSize() {
        return usersList.size();
    }
 
    @Override
    public Object getElementAt(int index) {
        User u=usersList.get(index);
        return u.getNickName();
    }
    
    
    
    public void addUser(User u){
        usersList.add(u);
        this.fireIntervalAdded(this, getSize(), getSize()+1);
       }
      public void deleteUser(int index)
      {
          usersList.remove(index);
          this.fireIntervalRemoved(index, getSize(), getSize()+1);
       }
      public User getUserByIndex(int index)
      {
      	return usersList.get(index);
      }

  	public ArrayList<User> getUsersList() {
  		return usersList;
  	}

  	public void setUsersList(ArrayList<User> localForumsList) {
  		this.usersList = localForumsList;
  	}
	

}
