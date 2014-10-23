class UserRegister
  
  def self.call(user)
    begin
      user.save!
    rescue Exception => e
      raise e.message
    end
  end
end