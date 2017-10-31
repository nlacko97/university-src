#include <stdlib.h>
#include <stdio.h>
#include <pwd.h>
#include <sys/types.h>
#include <unistd.h>

int main(int argc, char* argv[])
{
	struct passwd *pwd;
	uid_t uid = getuid();
	pwd = getpwuid(uid);
	if (pwd)
		printf("Home dir: %s\n", pwd->pw_dir);
	
	int bufsize = 256;
	char *buf = malloc(bufsize);
	setpwent();
	while((pwd = getpwent()) != NULL)
	{
	//	printf("Username: %s\n", pwd->pw_name);
	}
	endpwent();

}
