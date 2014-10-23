class UserLocked
  
  def self.call(user)
    raise "No user" unless user.present?
    raise "User is locked" if user.locked
    raise "Too many failed login attempts" if user.failed_attempts > 3
  end
  
end