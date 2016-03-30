#-------------------------------------------------
#
# Project created by QtCreator 2016-03-28T18:14:16
#
#-------------------------------------------------

QT       += core gui

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = conmandictionary
TEMPLATE = app


SOURCES += main.cpp\
        mainwindow.cpp \
    searchwidget.cpp \
    languagepanel.cpp

HEADERS  += mainwindow.h \
    searchwidget.h \
    languagepanel.h

FORMS    += mainwindow.ui \
    searchwidget.ui \
    languagepanel.ui
