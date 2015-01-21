require 'test_helper'

class SessionTest < ActionDispatch::IntegrationTest
  test 'login page is ok' do
    visit('/login')
    assert page.status_code == 200
  end

end
