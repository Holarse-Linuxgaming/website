require 'test_helper'

class UserTest < ActiveSupport::TestCase
  
  test "working fabrication" do
    assert Fabricate.build(:user).valid?
  end
  
  test "username must be present" do
    assert !Fabricate.build(:user, username: nil).valid?
  end
  
  test "email must be present" do
    assert !Fabricate.build(:user, email: nil).valid?
  end
  
  test "username is unique" do
    a = Fabricate(:user)
    
    assert !Fabricate.build(:user, username: a.username).valid?   
  end
  
  test "email is unique" do
    a = Fabricate(:user)
    
    assert !Fabricate.build(:user, email: a.email).valid?    
  end

end
