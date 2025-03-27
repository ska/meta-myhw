SUMMARY = "Linux Marvell kirkwood Kernel"
DESCRIPTION = "Compiled using information found at the link https://forum.doozan.com/read.php?2,12096 thanks to Bodhi"
SECTION = "kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

require recipes-kernel/linux/linux-yocto.inc
DEPENDS += "u-boot-mkimage-native"


SRC_URI = "https://cdn.kernel.org/pub/linux/kernel/v6.x/linux-${PV}.tar.xz"
SRC_URI[sha256sum] = "d450ab215de4e1f8bb85e0f4216760fa33fd024b4526b144f4ce0d9012b29c9e"

LINUX_VERSION = "6.12.6"

S = "${WORKDIR}/linux-${LINUX_VERSION}"
B = "${WORKDIR}/build"

#Defconfig
SRC_URI += "file://defconfig"
#Patches
SRC_URI += "file://0001-linux-6.12.6-kirkwood-tld-1.patch"

do_deploy:append() {
    if [ "${KERNEL_APPEND_DEVICETREE}" = "1" ]; then
        install -d ${DEPLOYDIR}/KernelBundleImages
        for dtbf in ${KERNEL_DEVICETREE};
        do
            dtb=$( basename $dtbf )
            cp  ${DEPLOYDIR}/zImage -L ${DEPLOYDIR}/KernelBundleImages/zImage-$dtb
            cat ${DEPLOYDIR}/$dtb >> ${DEPLOYDIR}/KernelBundleImages/zImage-$dtb
            #uboot-mkimage
            cd ${DEPLOYDIR}/KernelBundleImages/
            mkimage -A arm -O linux -T kernel -C none -a ${UBOOT_ENTRYPOINT} -e ${UBOOT_ENTRYPOINT} -n Linux-${LINUX_VERSION}-${MACHINE} -d zImage-$dtb  uImage-$dtb
            rm zImage-$dtb
            mv uImage-$dtb ../"$(basename "uImage-$dtb" | cut -d. -f1)".bin
            cd -
        done
        rm -r ${DEPLOYDIR}/KernelBundleImages
    fi
}
