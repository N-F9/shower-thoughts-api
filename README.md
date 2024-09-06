# 🚿 Shower Thoughts

[Shower Thoughts](https://showerthoughts.nickf.me/) is an API that gives easily accessible thoughts from [r/showerthoughts](https://reddit.com/r/showerthoughts). The documentation can be found on my [docs page](https://docs.nickf.me/projects/shower%20thoughts/).


<!-- TODO

v 0.1
- [x] handle special cases for API
- [x] basic frontend for random showerthoughts with links
- [x] documentation on docs.nickf.me
- [x] clean up
- [x] hosting on homelab and postgre setup
- [x] readme

v 0.2
- [x] code fixes and cleanup
- [x] fix build errors

v 0.3
- [ ] count endpoint with parameters
- [ ] report function -->

<!-- 
for building:
mvn clean package

cd /home/nick/shower-thoughts-api/data/
sudo nohup java -jar data.jar > /home/nick/logs/showerthoughts-data.log 2>&1 &
cd /home/nick/shower-thoughts-api/web/
sudo nohup java -jar web-0.0.1-SNAPSHOT.jar > /home/nick/logs/showerthoughts-web.log 2>&1 &
cd

fix the sudo -->