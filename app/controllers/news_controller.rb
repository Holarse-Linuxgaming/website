# -*- coding: utf-8 -*-
class NewsController < ApplicationController

  before_filter :login_required, except: [ :index, :show ]

  add_breadcrumb "News", "index"

  def index
    @news = News.all.decorate
    add_breadcrumb "Übersicht"
  end

  def new
    @news = News.new
    add_breadcrumb "Neuanlage"
  end

  def create
    @news = News.new(news_params)
    @news.user = current_user

    if @news.save
      flash[:success] = "Die News wurde gespeichert"
      redirect_to @news
    else
      flash[:error] = "Es gab einen Fehler beim Speichern des Artikels"
      render :edit
    end
  end

  def destroy
  end

  def show
    @news = News.friendly.find(params[:id]).decorate
    add_breadcrumb @news.title, @news
  end

  def edit
    @news = News.friendly.find(params[:id])
    add_breadcrumb @news.title, @news
    add_breadcrumb "Bearbeiten"
  end

  def update
    @news = News.friendly.find(params[:id])
    @news.update_attributes(news_params)
    @news.user = current_user

    if @news.save
      flash[:success] = "Die News wurde aktualisiert."
      redirect_to @news
    else
      flash[:error] = "Es gab ein Problem beim Speichern der News"
      render :edit
    end
  end

  private

  def news_params
    params.require(:news).permit(:title, :secondary_title, :content, :news_list)
  end

end
