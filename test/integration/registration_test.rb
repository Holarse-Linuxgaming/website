require 'test_helper'

class RegistrationTest < ActionDispatch::IntegrationTest

  test 'registration page is ok' do
    visit('/register')
    assert page.status_code == 200
  end

  test 'register a new user' do

    u1 = Fabricate.build(:user)

    visit('/register')
    within("#new_user") do
      fill_in 'Username', with: u1.username
      fill_in 'Email', with: u1.email
      fill_in 'Password', with: u1.password
      fill_in 'Password confirmation', with: u1.password

      click_button 'Register'
    end

    u2 = User.find_by_username(u1.username)
    assert u2.valid?
  end


end
