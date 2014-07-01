Fabricator(:user) do
  username { Forgery::Internet.user_name }
  email { Forgery::Internet.email_address }
  password { Forgery::Basic.password }
end

Fabricator(:legacy_user, from: :user) do
  password "asd"
  legacy_password { Forgery::Basic.password }
end
