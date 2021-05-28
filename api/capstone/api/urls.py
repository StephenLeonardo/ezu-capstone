from django.urls import path
from rest_framework import routers
from .views import getTranslation

app_name = 'api'

urlpatterns = [
    path('get-translation/', getTranslation, name='getTranslation')
]
