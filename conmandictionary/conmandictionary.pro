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
    languagepanel.cpp \
    dictionary_data.cpp \
    document_model_prototest.cpp

HEADERS  += mainwindow.h \
    languagepanel.h \
    dictionary_data.h

FORMS    += mainwindow.ui \
    languagepanel.ui

DISTFILES += \
    CMakeLists.txt
