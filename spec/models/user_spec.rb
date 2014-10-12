require 'rails_helper'

RSpec.describe User, :type => :model do 

  describe "Fields" do 
  	it "should have a valid factory" do  
      expect(Fabricate.build(:user)).to be_valid
  	end
    
    it "should require a username" do
      expect(Fabricate.build(:user, username: nil)).not_to be_valid
      expect(Fabricate.build(:user, username: "")).not_to be_valid
      expect(Fabricate.build(:user, username: " ")).not_to be_valid
    end
    
    it "should require a unique username" do
      a = Fabricate(:user)
      expect(a).to be_valid
      
      expect(Fabricate.build(:user, username: a.username)).not_to be_valid
    end    
    
    it "should require an email" do
      expect(Fabricate.build(:user, email: nil)).not_to be_valid
      expect(Fabricate.build(:user, email: "")).not_to be_valid
      expect(Fabricate.build(:user, email: " ")).not_to be_valid
    end    
    
    it "should require a unique email" do
      a = Fabricate(:user)
      expect(a).to be_valid
      
      expect(Fabricate.build(:user, email: a.email)).not_to be_valid
    end    

  end
  
  describe "Legacy" do
  	it "should create a user with a legacy password" do
    	a = Fabricate.build(:legacy_user)
      #expect(a).to be_valid
    
      expect(a.legacy_password).not_to be_nil
  	end
    
   	it "should recognize a legacy user" do
     	a = Fabricate.build(:legacy_user)
      expect(a.is_legacy?).to be(true)
   	end
    
   	it "should not recognize an empty or nil legacy_password as legacy" do
      expect(Fabricate.build(:legacy_user, legacy_password: nil).is_legacy?).to be(false)
      expect(Fabricate.build(:legacy_user, legacy_password: "").is_legacy?).to be(false)
      expect(Fabricate.build(:legacy_user, legacy_password: "   ").is_legacy?).to be(false)
   	end
 	end

end
