# encoding: UTF-8
# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema.define(version: 20150301193634) do

  create_table "articles", force: :cascade do |t|
    t.string   "title",           limit: 255,                 null: false
    t.string   "alternatives",    limit: 255
    t.text     "content",                                     null: false
    t.boolean  "unchangable",                 default: false
    t.boolean  "commentsallowed",             default: true
    t.string   "slug",            limit: 255
    t.datetime "created_at",                                  null: false
    t.datetime "updated_at",                                  null: false
    t.integer  "user_id"
  end

  add_index "articles", ["slug"], name: "index_articles_on_slug", unique: true
  add_index "articles", ["user_id"], name: "index_articles_on_user_id"

  create_table "comments", force: :cascade do |t|
    t.integer  "user_id"
    t.integer  "commentable_id"
    t.string   "commentable_type", limit: 255
    t.text     "content"
    t.boolean  "enabled",                      default: true
    t.datetime "created_at",                                  null: false
    t.datetime "updated_at",                                  null: false
  end

  add_index "comments", ["commentable_type", "commentable_id"], name: "index_comments_on_commentable_type_and_commentable_id"

  create_table "friendly_id_slugs", force: :cascade do |t|
    t.string   "slug",           limit: 255, null: false
    t.integer  "sluggable_id",               null: false
    t.string   "sluggable_type", limit: 50
    t.string   "scope",          limit: 255
    t.datetime "created_at"
  end

  add_index "friendly_id_slugs", ["slug", "sluggable_type", "scope"], name: "index_friendly_id_slugs_on_slug_and_sluggable_type_and_scope", unique: true
  add_index "friendly_id_slugs", ["slug", "sluggable_type"], name: "index_friendly_id_slugs_on_slug_and_sluggable_type"
  add_index "friendly_id_slugs", ["sluggable_id"], name: "index_friendly_id_slugs_on_sluggable_id"
  add_index "friendly_id_slugs", ["sluggable_type"], name: "index_friendly_id_slugs_on_sluggable_type"

  create_table "news", force: :cascade do |t|
    t.string   "title",           limit: 255,                 null: false
    t.string   "subtitle",        limit: 255
    t.text     "content"
    t.boolean  "unchangable",                 default: false
    t.boolean  "commentsallowed",             default: true
    t.string   "slug",            limit: 255
    t.datetime "created_at",                                  null: false
    t.datetime "updated_at",                                  null: false
    t.integer  "user_id"
  end

  add_index "news", ["slug"], name: "index_news_on_slug", unique: true
  add_index "news", ["user_id"], name: "index_news_on_user_id"

  create_table "roles", force: :cascade do |t|
    t.string   "name",       limit: 255
    t.string   "label",      limit: 255
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "roles_users", id: false, force: :cascade do |t|
    t.integer "user_id", null: false
    t.integer "role_id", null: false
  end

  create_table "taggings", force: :cascade do |t|
    t.integer  "tag_id"
    t.integer  "taggable_id"
    t.string   "taggable_type", limit: 255
    t.integer  "tagger_id"
    t.string   "tagger_type",   limit: 255
    t.string   "context",       limit: 128
    t.datetime "created_at"
  end

  add_index "taggings", ["tag_id", "taggable_id", "taggable_type", "context", "tagger_id", "tagger_type"], name: "taggings_idx", unique: true
  add_index "taggings", ["taggable_id", "taggable_type", "context"], name: "index_taggings_on_taggable_id_and_taggable_type_and_context"

  create_table "tags", force: :cascade do |t|
    t.string  "name",           limit: 255
    t.integer "taggings_count",             default: 0
  end

  add_index "tags", ["name"], name: "index_tags_on_name", unique: true

  create_table "users", force: :cascade do |t|
    t.string   "username",        limit: 255
    t.string   "email",           limit: 255
    t.datetime "created_at"
    t.datetime "updated_at"
    t.string   "password_digest", limit: 255
    t.boolean  "locked",                      default: false
    t.integer  "failed_attempts",             default: 0
    t.string   "slug",            limit: 255
  end

  add_index "users", ["email"], name: "index_users_on_email", unique: true
  add_index "users", ["slug"], name: "index_users_on_slug", unique: true
  add_index "users", ["username"], name: "index_users_on_username", unique: true

end
