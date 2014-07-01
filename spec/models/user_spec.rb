require 'spec_helper'

describe User do

  describe "Fields" do 
  	it "should have a valid factory" do  
    	Fabricate.build(:user).should be_valid 
  	end
    
    it "should require a username" do
      Fabricate.build(:user, username: nil).should_not be_valid
      Fabricate.build(:user, username: "").should_not be_valid
      Fabricate.build(:user, username: " ").should_not be_valid
    end
    
    it "should require a unique username" do
      a = Fabricate(:user)
      a.should be_valid
      
      Fabricate.build(:user, username: a.username).should_not be_valid
    end    
    
    it "should require an email" do
      Fabricate.build(:user, email: nil).should_not be_valid
      Fabricate.build(:user, email: "").should_not be_valid
      Fabricate.build(:user, email: " ").should_not be_valid
    end    
    
    it "should require a unique email" do
      a = Fabricate(:user)
      a.should be_valid
      
      Fabricate.build(:user, email: a.email).should_not be_valid
    end    

  end
  
  describe "Legacy" do
  	it "should create a user with a legacy password" do
    	a = Fabricate.build(:legacy_user)
    	a.should be_valid
    
    	a.legacy_password.should_not be_nil
  	end
    
   	it "should recognize a legacy user" do
     	Fabricate.build(:legacy_user).is_legacy?.should be_true
   	end
    
   	it "should not recognize an empty or nil legacy_password as legacy" do
     	Fabricate.build(:legacy_user, legacy_password: nil).is_legacy?.should_not be_true
     	Fabricate.build(:legacy_user, legacy_password: "").is_legacy?.should_not be_true
     	Fabricate.build(:legacy_user, legacy_password: "   ").is_legacy?.should_not be_true
   	end
 	end

end
