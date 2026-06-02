# CI/CD Guide

Du an su dung GitHub Actions de tu dong build, test, dong goi Docker image, push len Docker Hub va deploy len server qua SSH.

Workflow chinh: `.github/workflows/ci-cd.yml`.

## Luong pipeline

1. Pull request vao `main` hoac `master`
   - Checkout source.
   - Khoi dong MySQL 8.4 trong GitHub Actions.
   - Nap schema tu `db-data/meal_planner_schema.sql`.
   - Chay `./mvnw -B clean verify`.
   - Upload file JAR trong `target/*.jar`.

2. Push len `main` hoac `master`
   - Chay toan bo buoc CI o tren.
   - Build Docker image bang `Dockerfile`.
   - Push image len Docker Hub voi 2 tag:
     - `latest` tren default branch.
     - `sha-<commit>` cho rollback.
   - Neu branch la default branch, deploy image `latest` len server qua SSH.

3. Chay thu cong
   - Vao GitHub Actions -> `CI/CD` -> `Run workflow`.

## GitHub Secrets

Them cac secret sau trong GitHub repository:

| Secret | Muc dich |
| --- | --- |
| `DOCKERHUB_USERNAME` | Docker Hub username |
| `DOCKERHUB_TOKEN` | Docker Hub access token |
| `DEPLOY_HOST` | IP/domain server deploy |
| `DEPLOY_USER` | User SSH tren server |
| `SSH_PRIVATE_KEY` | Private key dung de SSH vao server |

Khuyen nghi tao GitHub Environment ten `production` va bat required reviewers neu deploy that.

## Chuan bi server deploy

Server can co Docker va file env cho ung dung:

```bash
sudo mkdir -p /opt/meal-planner
sudo nano /opt/meal-planner/.env
```

Noi dung mau:

```env
SPRING_PROFILES_ACTIVE=cloud
DB_HOST=your-db-host
DB_PORT=3306
DB_NAME=meal_planner_system
DB_USERNAME=your-db-user
DB_PASSWORD=your-db-password
JWT_SECRET=replace-with-a-long-random-secret
JWT_EXPIRATION_MS=86400000
JWT_REFRESH_EXPIRATION_MS=604800000
```

Workflow se chay container:

```bash
docker run -d \
  --name meal-planner-system \
  --restart unless-stopped \
  --env-file /opt/meal-planner/.env \
  -p 8080:8080 \
  <dockerhub-username>/meal-planner-system:latest
```

## Kiem tra local truoc khi push

```bash
.\mvnw.cmd clean verify
docker build -t meal-planner-system:local .
docker run --env-file .env -p 8080:8080 meal-planner-system:local
```

## Rollback

Moi image duoc gan tag theo commit SHA. De rollback tren server:

```bash
docker pull <dockerhub-username>/meal-planner-system:sha-<commit>
docker stop meal-planner-system || true
docker rm meal-planner-system || true
docker run -d \
  --name meal-planner-system \
  --restart unless-stopped \
  --env-file /opt/meal-planner/.env \
  -p 8080:8080 \
  <dockerhub-username>/meal-planner-system:sha-<commit>
```
