require 'test_helper'

class WelcomeTest < ActionDispatch::IntegrationTest
  test 'welcome page is ok' do
    visit('/')
    assert page.has_content?("Willkommen auf Holarse")
    assert page.status_code == 200
  end

end
