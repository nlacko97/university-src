#include <stdlib.h>
#include <stdio.h>
#include <pwd.h>
#include <sys/types.h>
#include <unistd.h>

int main(int argc, char* argv[])
{
	struct passwd *pwd;

	uid_t uid = getuid();
	// pwd = getpwnam("udvarhet");
	pwd = getpwuid(uid);

	if (pwd)
		printf("Home dir: %s\nName: %s\n", pwd->pw_dir, pwd->pw_name);

	setpwent();

	while((pwd = getpwent()) != NULL)
	{
		//printf("%s\n", pwd->pw_name);
	}

	endpwent();

}
