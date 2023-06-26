FROM python:3.8-slim-buster
WORKDIR /app

ENV FLASK_APP=app.py
ENV FLASK_RUN_HOST=0.0.0.0
ENV FLASK_ENV=development

COPY /requirements.txt requirements.txt
RUN pip install -r requirements.txt

COPY . .

EXPOSE 5001
CMD [ "flask", "run","--host","0.0.0.0","--port","5001"]