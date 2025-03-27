# Copyright (C) 2025 Devel <ska@linux.it>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "U-Boot port for Marvell Kirkwood"
HOMEPAGE = "https://www.denx.de/project/u-boot/"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=30503fd321432fc713238f582193b78e"
SECTION = "bootloader"
DEPENDS = ""
DEPENDS += "flex-native bison-native python3-setuptools-native"

SRC_URI = "https://repository.timesys.com/buildsources/u/u-boot/u-boot-2020.04/u-boot-2020.04.tar.bz2"
SRC_URI[sha256sum] = "fe732aaf037d9cc3c0909bad8362af366ae964bbdac6913a34081ff4ad565372"

require recipes-bsp/u-boot/u-boot.inc

S = "${WORKDIR}/u-boot-2020.04"
B = "${WORKDIR}/build"

inherit pkgconfig

do_configure[cleandirs] = "${B}"

