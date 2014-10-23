Fabricator(:user) do
  username { Faker::Internet.user_name }
  email { Faker::Internet.email }
  password { Faker::Internet.password }
  locked false
  failedattempts 0
end

Fabricator(:legacy_user, from: :user) do
  password "asd"
  legacy_password { Forgery::Internet.password }
end