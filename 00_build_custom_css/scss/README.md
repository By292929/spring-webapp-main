

# use npm to install sass
```
npm install -g sass
```

# download bootstrap source and get the custom.css


```
wget https://github.com/twbs/bootstrap/archive/v5.3.8.zip
unzip v5.3.8.zip
mv -r bootstrap-5.3.8/scss/* bootstrap/
sass scss/custom.scss css/custom.css
```
