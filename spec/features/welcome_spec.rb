require 'spec_helper'

describe 'the welcome page', type: :feature do
  
  it 'should load as root' do
    visit root_path
    expect(page).to have_text("Willkommen auf Holarse")
  end
  
end