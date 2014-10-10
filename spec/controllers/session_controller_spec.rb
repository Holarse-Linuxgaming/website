require 'spec_helper'

describe SessionController do

  describe "GET 'login'" do
    it "returns http success" do
      get 'login'
      response.should be_success
    end
  end

  describe "GET 'logout'" do
    it "returns http success" do
      get 'logout'
      response.should be_success
    end
  end

end
