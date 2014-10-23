class AddUserLockingFields < ActiveRecord::Migration
  def change
    add_column :users, :locked, :boolean, default: false
    add_column :users, :failed_attempts, :integer, default: 0
  end
end
