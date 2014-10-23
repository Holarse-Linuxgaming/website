require 'test_helper'

class UserLockedTest < ActiveSupport::TestCase
  
  test "user is locked" do
    ex = assert_raise(RuntimeError) {
      u = Fabricate(:user, locked: true)
      UserLocked.call(u)
    }
    
    assert_equal("User is locked", ex.message)
  end

#  test "user can retry so many times to login" do
#    u = Fabricate(:user)
#    u.failed_attempts = 2
#    UserLocked.call(u)
#  end  
#  
#  test "user tried to login too many times" do
#    ex = assert_raise(RuntimeError) {
#      u = Fabricate(:user)
#      u.failed_attempts = 5
#      UserLocked.call(u)
#    }
#    
#    assert_equal("Too many failed login attempts", ex.message)
#  end
  
end