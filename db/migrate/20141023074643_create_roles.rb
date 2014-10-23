class CreateRoles < ActiveRecord::Migration
  def change
    create_table :roles do |t|
      t.string :name
      t.string :label
      
      t.timestamps
    end
    
    create_join_table :users, :roles
  end
end
