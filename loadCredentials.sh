#!/bin/bash
file=~/.aws/credentials
if [ -f "$file" ]
then
  while IFS='=' read -r key value
  do
      if [[ $key =~ ^[a-zA-Z_0-9].*$ ]]
      then
        key=$(echo $key | tr '.' '_')
        noSpace="$(sed -e 's/[[:space:]]*$//' <<<${value})"
        eval "${key}='${noSpace}'"
      fi
  done < "$file"
else
  echo "AWS Credentials file not found"
fi

