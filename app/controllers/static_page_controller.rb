#encoding: utf-8
class StaticPageController < ApplicationController

  def about
    add_breadcrumb "Über Holarse", about_path
  end

  def imprint
    add_breadcrumb "Impressum", imprint_path
  end

  def privacy
    add_breadcrumb "Datenschutzerklärung", privacy_path
  end

end
