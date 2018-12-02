jQuery('.skillbar').each(function(){
	jQuery(this).find('.skillbar-bar').animate({
	
		width:jQuery(this).attr('data-percent')
	},2000,function(){
		console.log(jQuery(this).attr('data-percent'))
	});
});