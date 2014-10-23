class UserLogin
  
  def self.call(username, password)
    Rails.logger.debug(username)
    Rails.logger.debug(password)
    
    user = User.find_by_username(username)
    raise "Benutzername oder Passwort falsch" unless user
    
    UserLocked.call(user)
    
    raise "Benutzername oder Passwort falsch" unless user.authenticate(password)
    
    user
  end
  
end