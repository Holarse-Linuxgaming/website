class CreateUsers < ActiveRecord::Migration
  def change
    create_table :users do |t|
      t.string :username
      t.string :email
      t.string :password_digest
      t.string :legacy_password
      
      t.boolean :active, default: true
      
      t.timestamps
    end
  end
end
