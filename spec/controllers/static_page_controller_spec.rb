require 'rails_helper'

RSpec.describe StaticPageController, :type => :controller do

  describe "GET imprint" do
    it "returns http success" do
      get :imprint
      expect(response).to have_http_status(:success)
    end
  end

end
