#encoding: utf-8
class StaticPageController < ApplicationController
  def about
    add_breadcrumb "Über", about_path
  end
end
