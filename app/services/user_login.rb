class UserLogin
  
  def self.call(params)
    user = User.find_by_username(params[:username])
    raise "Benutzername oder Passwort falsch" unless user
    
    UserLocked.call(user)
    
    raise "Benutzername oder Passwort falsch" unless user.authenticate(params[:password])
    
    user
  end
  
end